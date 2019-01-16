export class PhoneSubscription {
    id: number;
    provider: string;

    sms: number;
    minutes: number;
    internetGB: number;

    price: number;
    connectionCost: number;
    discount: boolean;
    renwableAfter: number;
    
    description: string;
}