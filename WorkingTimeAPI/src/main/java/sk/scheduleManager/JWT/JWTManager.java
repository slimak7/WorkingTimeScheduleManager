package sk.scheduleManager.JWT;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import sk.scheduleManager.Models.Employee;

public class JWTManager {

    public static final long JWT_TOKEN_VALIDITY = 60 * 60;

    private static final String secret = "558kklopnnbh2222222222222222222222222455555555555jjjjjjnnnhgggggggggggkkkkkk" +
            "kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkmmmmmmmmmujhgttffffffffffffffffffffffcccccccc7856588";

    //retrieve username from jwt token
    public String GetUsernameFromToken(String token) {
        return GetClaimFromToken(token, Claims::getSubject);
    }

    //retrieve expiration date from jwt token
    public Date GetExpirationDateFromToken(String token) {
        return GetClaimFromToken(token, Claims::getExpiration);
    }

    public String GetFieldFromToken(String token, String key) {
        return GetClaimFromToken(token, x -> (String) x.get(key));
    }

    public <T> T GetClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = GetAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    //for retrieveing any information from token we will need the secret key
    private Claims GetAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }


    private Boolean IsTokenExpired(String token) {
        final Date expiration = GetExpirationDateFromToken(token);
        return expiration.before(new Date());
    }


    public static String GenerateToken(Employee employee) {
        Map<String, Object> claims = new HashMap<>();
        String position = employee.isAdmin() ? "admin" : "default";
        claims.put("userType", position);
        return DoGenerateToken(claims, employee.getLogin());
    }

    private static String DoGenerateToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }


    public Boolean ValidateToken(String token, Employee employee) {
        final String username = GetUsernameFromToken(token);
        return (username.equals(employee.getLogin()) && !IsTokenExpired(token));
    }

}
