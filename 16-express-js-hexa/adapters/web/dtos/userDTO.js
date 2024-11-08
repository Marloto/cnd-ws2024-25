import {User} from '../../../domain/models/user.js'

export class UserDTO {
    constructor(id, name, email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    static fromDomain(user) {
        return new UserDTO(
            user.id,
            user.name,
            user.email
        );
    }
}


