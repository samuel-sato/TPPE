import { BaseEntity } from "./BaseEntity";

export interface Seller extends BaseEntity{
    name: string;
    email: string;
    password: string;
    birthdate: Date;
    baseSalary: number;
    numberHours: number;
}