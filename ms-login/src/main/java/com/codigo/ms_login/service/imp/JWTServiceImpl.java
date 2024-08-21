package com.codigo.ms_login.service.imp;

import com.codigo.ms_login.service.JWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoder;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTServiceImpl implements JWTService {

    @Value("${key.token}")
    private String keytoken;

    @Value("${key.milis}")
    private Long keymilis;

    @Override
    public String extracUserName(String token) {
        System.out.println("extracUserName token: " + token);
        return extractClaims(token, Claims::getSubject);
    }

    @Override
    public String generateToken(UserDetails userDetails) {

        return Jwts.builder().setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + keymilis))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Del TOKEN, se va a validar que sea el mismo usuario que tenemos en BD y que la fecha del TOKEN no esté expirado
    @Override
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extracUserName(token);
        return ( username.equals(userDetails.getUsername()) && !isTokenExpired(token) );
    }

    @Override
    public String generateRefreshToken(Map<String, Object> extraclaims, UserDetails userDetails) {
        return "";
    }

    // METODOS DE APOYO; para no repetir codigo y que no sean extensivos

    // una llave que cumpla con los 256 bytes y usamos el algoritmo de 256 bytes
    // La clave de 256bytes lo decodificamos de base64
    // hmacShaKey, nos permite generar una clave secreta para firmar el token
    // Decoders -> io.jsonwebtoken.io
    // Keys --> io.jsonwebtoken.security
    private Key getSignKey(){
        byte[] key = Decoders.BASE64.decode(keytoken);
        return Keys.hmacShaKeyFor(key);
    }

    // Para extraer todos los claims (contenido) del token
    // devuelve un objeto Claims
    // Claims -> io.jsonwebtoken
    private Claims extracAllClaims(String token){
        return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
    }

    // Para Extraer un solo CLAIMS
    // argumentos: un token, el CLAIMS y cual claims que existen deseamos
    private <T> T extractClaims(String token, Function<Claims, T> claimsResult){
        final Claims claims = extracAllClaims(token);
        return claimsResult.apply(claims);
    }

    // METODO PARA VALIDAR UN TOKEN QUE NO ESTE EXPIRADO
    // extraemos del token el CLAIM de fecha de expiración y lo comparamos si está antes de la fecha actual,
    //      si está antes, entonces ya expiro
    private boolean isTokenExpired(String token){
        return extractClaims(token, Claims::getExpiration).before(new Date());
    }
    public boolean isTokenExpired2(String token){
        return extractClaims(token, Claims::getExpiration).before(new Date());
    }

}
