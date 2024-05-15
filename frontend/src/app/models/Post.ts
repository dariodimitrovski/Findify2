import { Municipality } from "./Municipality";
import { User } from "./User";
import { Category } from "./Category";
import { Location } from "./Location";

export interface Post{
    id: number;
    title: string,
    description: string
    state: string;
    image: any;
    user: User;
    municipality: Municipality;
    category: Category;
    comments: Comment[];
    flag: boolean;
    location: Location;
    time: String;
}