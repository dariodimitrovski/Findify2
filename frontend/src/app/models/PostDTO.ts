import { Category } from "./Category";
import { Municipality } from "./Municipality";
import { PostStatus } from "./PostStatus";
import { User } from "./User";

export interface PostDTO {
    title: string,
    category: Category,
    description: string,
    municipality: Municipality,
    image: any,
    state: string,
    // user: User
}