import { BaseEntity } from './../../shared';

export class Property implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public beers?: BaseEntity[],
        public wines?: BaseEntity[],
    ) {
    }
}
