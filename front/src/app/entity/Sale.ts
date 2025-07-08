import { BaseEntity } from "./BaseEntity";
import { Client } from "./Client";
import { ProductList } from "./ProductList";
import { Seller } from "./Seller";

export interface Sale extends BaseEntity{
    dateSale: Date;
    price: number;
    products: ProductList[];
    seller: Seller;
    client: Client;
}