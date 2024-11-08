import express from 'express';
import { UserDTO } from './dtos/userDTO.js';

export class ExpressAdapter {
    constructor(userService) {
        this.userService = userService;
        this.app = express();
        this.app.use(express.json());
        this.setupRoutes();
    }

    setupRoutes() {
        this.app.post('/users', async (req, res) => {
            try {
                // Validierung des Eingangs-DTO
                const createUserDTO = this.validateCreateUserDTO(req.body);
                
                // Service-Aufruf
                const createdUser = await this.userService.createUser(createUserDTO.name, createUserDTO.email, createUserDTO.password); 
                
                // Umwandlung in Response-DTO
                const userDTO = UserDTO.fromDomain(createdUser);
                
                res.status(201).json(userDTO);
            } catch (error) {
                console.log(error);
                res.status(400).json({ error: error.message });
            }
        });

        this.app.get('/users/:id', async (req, res) => {
            try {
                const user = await this.userService.getUserById(req.params.id);
                // Explizite Umwandlung in DTO für Response
                const userDTO = UserDTO.fromDomain(user);
                res.json(userDTO);
            } catch (error) {
                if (error.message === 'User not found') {
                    res.status(404).json({ error: error.message });
                } else {
                    res.status(500).json({ error: error.message });
                }
            }
        });
    }

    // Validierung des Eingangs-DTO
    validateCreateUserDTO(data) {
        if (!data.name || typeof data.name !== 'string') {
            throw new Error('Name is required and must be a string');
        }
        if (!data.email || !data.email.includes('@')) {
            throw new Error('Valid email is required');
        }
        if (!data.password || typeof data.password !== 'string') {
            throw new Error('Valid password is required');
        }
        
        return {
            name: data.name.trim(),
            email: data.email.toLowerCase().trim(),
            password: data.password
        };
    }

    start(port) {
        this.app.listen(port, () => {
            console.log(`Server running on port ${port}`);
        });
    }
}