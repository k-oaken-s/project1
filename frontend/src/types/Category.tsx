import {Item} from "./Item";

export interface Category {
    id: string;
    name: string;
    description: string;
    image: string
    items: Item[];
    createdAt: string;
}
