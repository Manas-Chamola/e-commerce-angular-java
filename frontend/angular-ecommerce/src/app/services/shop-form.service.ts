import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ShopFormService {
  constructor() {}

  getCreditcardMonths(startMonth: number): Observable<number[]> {
    let data: number[] = [];

    //build array of Month for dropdown list

    for (let theMonth = startMonth; theMonth <= 12; theMonth++) {
      data.push(theMonth);
    }

    return of(data);
  }

  getCreditcardYears(): Observable<number[]> {
    let data: number[] = [];

    //build array of year for dropdown list

    const startYear: number = new Date().getFullYear();
    const endYear: number = startYear + 10;

    for (let theYear = startYear; theYear <= endYear; theYear++) {
      data.push(theYear);
    }

    return of(data);
  }
}
