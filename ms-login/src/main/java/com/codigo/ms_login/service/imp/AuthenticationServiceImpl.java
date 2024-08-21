package com.codigo.ms_login.service.imp;

import com.codigo.ms_login.client.ClientReniec;
import com.codigo.ms_login.client.ClientTrab;
import com.codigo.ms_login.constants.Constant;
import com.codigo.ms_login.dto.ReniecDto;
import com.codigo.ms_login.dto.TrabDto;
import com.codigo.ms_login.entities.AreaEntity;
import com.codigo.ms_login.entities.RolEntity;
import com.codigo.ms_login.entities.UsuarioEntity;
import com.codigo.ms_login.redis.RedisService;
import com.codigo.ms_login.repository.AreaRepository;
import com.codigo.ms_login.repository.RolRepository;
import com.codigo.ms_login.repository.UsuarioRepository;
import com.codigo.ms_login.request.UsuarioAreaRequest;
import com.codigo.ms_login.request.SignInRequest;
import com.codigo.ms_login.request.UsuarioRequest;
import com.codigo.ms_login.response.AuthenticationResponse;
import com.codigo.ms_login.response.BaseResponse;
import com.codigo.ms_login.service.AuthenticationService;
import com.codigo.ms_login.service.JWTService;
import com.codigo.ms_login.util.Util;
import feign.FeignException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.sql.Date;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final AreaRepository areaRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final ClientReniec clientReniec;
    private final ClientTrab clientTrab;
    private final RedisService redisService;

    @Value("${token.reniec}")
    private String tokenReniec;

    @Transactional
    @Override
    public ResponseEntity<BaseResponse> signUpUser(UsuarioRequest usuarioRequest) {
        // Validando que se ingrese un valor de USUARIO_COD en el request
        if (ObjectUtils.isEmpty(usuarioRequest) || usuarioRequest.getUsuarioCod().isEmpty()) {
            BaseResponse<UsuarioEntity> baseResponse = new BaseResponse<>(Constant.CODE_VALOR_NOVALIDO, Constant.MSJ_VALOR_NOVALIDO, Optional.empty());
            return ResponseEntity.ok(baseResponse);
        }
        // Validando que el Usuario No exista
        boolean exist = usuarioRepository.existsByUsuarioCod(usuarioRequest.getUsuarioCod());
        if (exist) {
            BaseResponse<UsuarioEntity> baseResponse = new BaseResponse<>(Constant.CODE_EXIST, Constant.MSJ_EXIST, Optional.empty());
            return ResponseEntity.ok(baseResponse);
        }

        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity = getEntity(usuarioRequest, usuarioEntity);

        // Extraendo el Nombre de REDIS
        String redisInfo = redisService.getFromRedis(Constant.REDIS_KEY_OBTENERPERSONA+usuarioEntity.getUsuarioDni());
        if (redisInfo!=null){
            usuarioEntity.setUsuarioNom(Util.convertirDesdeString(redisInfo,TrabDto.class).getNom());
        } else {
            // Si el Usuario no es trabajor obtener sus datos de la RENIEC, Caso Contrario del API del Maestro
            if (usuarioRequest.getUsuarioEstrabajador().equals(Constant.VALOR_NO)) {
                try {
                    ReniecDto reniecDto = getExecReniec(usuarioEntity.getUsuarioDni());
                    usuarioEntity.setUsuarioNom(reniecDto.getApellidoPaterno() + ' ' + reniecDto.getApellidoMaterno() + ' ' + reniecDto.getNombres());
                } catch (FeignException e) {
                    BaseResponse<UsuarioEntity> baseResponse = new BaseResponse<>(Constant.CODE_VALOR_NOVALIDO, Constant.MSJ_VALOR_NOVALIDO, Optional.empty());
                    return ResponseEntity.ok(baseResponse);
                }
            } else {
                try {
                    TrabDto trabDto = getExecTrab(usuarioEntity.getUsuarioDni());
                    usuarioEntity.setUsuarioNom(trabDto.getNom());
                } catch (FeignException e) {
                    BaseResponse<UsuarioEntity> baseResponse = new BaseResponse<>(Constant.CODE_VALOR_NOVALIDO, Constant.MSJ_VALOR_NOVALIDO, Optional.empty());
                    return ResponseEntity.ok(baseResponse);
                }
            }
            TrabDto trabDto2 = new TrabDto();
            trabDto2.setDni(usuarioEntity.getUsuarioDni());
            trabDto2.setNom(usuarioEntity.getUsuarioNom());
            String dataForRedis = Util.convertirAString(trabDto2);
            redisService.saveInRedis(Constant.REDIS_KEY_OBTENERPERSONA+trabDto2.getDni(),dataForRedis,Constant.REDIS_EXPIRE_MINUTES);

        }
        usuarioEntity.setUsuarioClave(new BCryptPasswordEncoder().encode(usuarioRequest.getUsuarioClave()));
        usuarioEntity.setEliminado(Constant.VALOR_NO);
        usuarioEntity.setCreaUsuarioCod(Constant.USUARIO_ADMIN);
        usuarioEntity.setCreaFecha(getTimestamp());

        BaseResponse<UsuarioEntity> baseResponse = new BaseResponse<>(Constant.CODE_OK, Constant.MSJ_OK, Optional.of(usuarioRepository.save(usuarioEntity)));
        return ResponseEntity.ok(baseResponse);
    }

    private ReniecDto getExecReniec(String numDoc){
        String authorization = "Bearer "+tokenReniec;
        return clientReniec.getInfoReniec(numDoc,authorization);
    }
    private TrabDto getExecTrab(String numDoc){
        return clientTrab.getInfoTrab(numDoc);
    }

    private UsuarioEntity getEntity(UsuarioRequest usuarioRequest, UsuarioEntity entity) {
        entity.setUsuarioCod(usuarioRequest.getUsuarioCod());
        entity.setUsuarioDni(usuarioRequest.getUsuarioDni());
        entity.setUsuarioEstrabajador(usuarioRequest.getUsuarioEstrabajador());
        entity.setGestionXArea(usuarioRequest.getGestionXArea());
        entity.setCuentaFechatermino(usuarioRequest.getCuentaFechatermino());
        entity.setCuentaBloqueada(usuarioRequest.getCuentaBloqueada());
        entity.setCredencialExpirado(usuarioRequest.getCredencialExpirado());
        entity.setEliminado(usuarioRequest.getEliminado());
        entity.setEliminadoMotivo(usuarioRequest.getEliminadoMotivo());
        // Validando la Existencia de Roles Ingresados y Seteando los Valores Validos
        Set<RolEntity> rolesEnumList = usuarioRequest.getRoles().stream()
                .filter(val -> rolRepository.existsByRolCod(val.getRolCod()))
                .collect(Collectors.toSet());

        entity.setRoles(rolesEnumList);

        // Validando la Existencia de Areas Ingresados y Seteando los Valores Validos
        Set<UsuarioAreaRequest> areasEnumList = usuarioRequest.getAreas().stream()
                .filter(val -> areaRepository.existsByAreaCod(val.getAreaCod()))
                .collect(Collectors.toSet());

        Set<AreaEntity> areaEntities = areasEnumList.stream().map(var -> areaRepository.findById(var.getAreaCod()).get()).collect(Collectors.toSet());

        entity.setAreas(areaEntities);

        return entity;
    }

    private Date getTimestamp() {
        long currenTIme = System.currentTimeMillis();
        return new Date(currenTIme);
    }


    @Override
    public AuthenticationResponse signin(SignInRequest singInRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                singInRequest.getUsuarioCod(), singInRequest.getPassword()));

        var user = usuarioRepository.findByUsuarioCod(singInRequest.getUsuarioCod()).orElseThrow(
                () -> new IllegalArgumentException("Email no valido"));

        var jwt = jwtService.generateToken(user);

        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setToken(jwt);
        authenticationResponse.setUsuarioNom(user.getUsuarioNom());
        authenticationResponse.setUsuarioRol(user.getRoles());
        return authenticationResponse;
    }

}
