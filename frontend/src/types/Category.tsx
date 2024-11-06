
export interface Item {
    id: string;
    name: string;
    description: string;
    image: string
}


export interface Category {
    id: string;
    name: string;
    description: string;
    image: string
    items: Item[];
}
