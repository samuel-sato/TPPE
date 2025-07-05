import { BaseEntity } from "./BaseEntity";

export interface Product extends BaseEntity {
    name: string;
    price: number;
    description: string;
    department: string;
    idDepartment: number;
}