import { Post } from "./Post";
import { User } from "./User";

export interface Comment{
    id: number;
    comment: string;
    post: Post;
    user: User;
}