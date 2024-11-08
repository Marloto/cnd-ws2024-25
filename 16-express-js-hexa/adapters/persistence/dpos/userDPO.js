import { User } from '../../../domain/models/user.js'

export class UserDPO {
    constructor(id, name, email, passwordHash, role, lastLogin) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password_hash = passwordHash; // Angepasst an DB-Konvention
        this.user_role = role;            // Angepasst an DB-Konvention
        this.last_login = lastLogin;      // Angepasst an DB-Konvention
    }

    static fromDomain(user) {
        return new UserDPO(
            user.id,
            user.name,
            user.email,
            user.passwordHash,
            user.role,
            user.lastLogin
        );
    }

    static toDomain(dpo) {
        return new User(
            dpo.id,
            dpo.name,
            dpo.email,
            dpo.password_hash,
            dpo.user_role,
            dpo.last_login
        );
    }
}