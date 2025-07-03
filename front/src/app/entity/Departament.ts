import { BaseEntity } from "./BaseEntity";
import { ProductList } from "./ProductList";

export interface Department extends BaseEntity{
    name: string;
    description: string,
    products: ProductList[]
}