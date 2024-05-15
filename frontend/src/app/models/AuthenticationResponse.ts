import { User } from "./User";

export interface AuthenticationResponse{
    jwt: string;
    expiresIn: string;
    user: string
}