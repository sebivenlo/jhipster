import { BaseEntity } from './../../shared';

export class Wine implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public price?: number,
        public quantity?: number,
        public beverageChart?: BaseEntity,
        public properties?: BaseEntity[],
    ) {
    }
}
