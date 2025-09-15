package fixtures;

import com.facilit.kanban_backend.utils.JwtUtil;

public class TokenFixtures {

    public static String getAcessToken() {
        return JwtUtil.generateToken(getIdUsuarioLogado().toString());
    }

    public static Long getIdUsuarioLogado() {
        return 1L;
    }
}
