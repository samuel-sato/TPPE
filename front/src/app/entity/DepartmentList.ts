import { BaseEntity } from "./BaseEntity";

export interface DepartmentList extends BaseEntity{
    name: string;
    description: string;
}