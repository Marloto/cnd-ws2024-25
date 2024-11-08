import {UserService} from '../domain/userService.js'
import {User} from '../domain/models/user.js'
export class UserServiceImpl extends UserService {
    constructor(userRepository) {
        super();
        this.userRepository = userRepository;
    }

    async createUser(name, email, password) {
        const user = new User(Date.now(), name, email, password, 'USER', null, Date.now());
        this.validateUser(user);
        return await this.userRepository.saveUser(user);
    }

    async getUserById(id) {
        const user = await this.userRepository.findUserById(id);
        if (!user) {
            throw new Error('User not found');
        }
        return user;
    }

    validateUser(user) {
        if (user.name.length < 2) {
            throw new Error('Name must be at least 2 characters long');
        }
    }
}