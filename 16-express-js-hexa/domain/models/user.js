export class User {
    constructor(id, name, email, passwordHash, role, lastLogin) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash; // Sensible Information
        this.role = role;
        this.lastLogin = lastLogin;
    }
}
