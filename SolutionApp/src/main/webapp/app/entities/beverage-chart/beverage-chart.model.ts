import { BaseEntity, User } from './../../shared';

export class BeverageChart implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public intro?: string,
        public user?: User,
    ) {
    }
}
