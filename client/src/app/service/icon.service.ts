import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class IconService {

  private categories: Map<string, string> = new Map([
    ['boodschappen', 'shopping-bag'],
    ['consumptie', 'utensils'],
    ['huishouden', 'home'],
    ['educatie', 'graduation-cap'],
    ['inkomsten', 'coins'],
    ['kleding', 'tshirt'],
    ['medischeKosten', 'stethoscope'],
    ['overigeUitgaven', 'ellipsis-h'],
    ['telecom', 'phone'],
    ['vervoer', 'car'],
    ['verzekeringen', 'shield-alt'],
    ['vrijeTijd', 'futbol'],
    ['vasteLast', 'undo']
  ]);

  constructor(
  ){}

  getIconByCategory(category: string): string {
    return this.categories.get(category);
  }

}
