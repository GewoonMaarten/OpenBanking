import { User } from './user.model';

export class BankAccount {
    id: number;
    iban: string;
    user: User;

    product: string;

    balance: number;
    balancechange: number;
    predictedBalance: number;
    predictedBalanceChange: number;
}