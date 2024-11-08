import { UserRepository } from '../../application/userRepository.js'
import { UserDPO } from './dpos/userDPO.js';

export class InMemoryUserRepository extends UserRepository {
    constructor() {
        super();
        this.users = new Map();
    }

    saveUser(user) {
        // Explizite Umwandlung in DPO
        const userDPO = UserDPO.fromDomain(user);
        this.users.set(`${userDPO.id}`, userDPO);
        console.log(this.users, userDPO.id, userDPO, user.id);
        return Promise.resolve(UserDPO.toDomain(userDPO));
    }

    findUserById(id) {
        const userDPO = this.users.get(`${id}`);
        console.log(this.users, id);
        if (!userDPO) return Promise.resolve(null);
        // Explizite Umwandlung zurück in Domain-Modell
        return Promise.resolve(UserDPO.toDomain(userDPO));
    }
}
