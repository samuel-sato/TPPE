import { BaseEntity } from "./BaseEntity";

export interface Client extends BaseEntity{
    name: string;
    email: string,
    password: string,
    birthdate: Date,
    notifyPromotion: boolean
}