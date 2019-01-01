import {Transaction} from './transaction.model';

export class IsRecurringPrediction {
  id: number;
  label: string;
  p0: number;
  p1: number;
  transaction: Transaction;
}
