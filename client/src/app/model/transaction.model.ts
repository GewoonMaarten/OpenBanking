import {IsRecurringPrediction} from './isRecurringPrediction.model';
import {CategoryPrediction} from './categoryPrediction.model';

export class Transaction {
  id: string;
  amount: number;
  name: string;
  date: Date;
  isRecurringPrediction: IsRecurringPrediction;
  categoryPrediction: CategoryPrediction;
  wordEmbbeding: number[];
}
