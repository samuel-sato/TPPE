import { BaseEntity } from "./BaseEntity";

export interface ProductList extends BaseEntity {
    name: string;
    price: number
}