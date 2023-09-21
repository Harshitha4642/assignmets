import { Component } from '@angular/core';

@Component({
  selector: 'app-odometer',
  templateUrl: './odometer.component.html',
  styleUrls: ['./odometer.component.css']
})
export class OdometerComponent {
  odoReading: number = 0;
  increment(): any{
    if (this.isAscending(this.odoReading)) {
      const readArray = this.makeArray(this.odoReading);
      const max = [9, 8, 7, 6, 5, 4, 3, 2, 1];
      const size = readArray.length;
      let i = size - 1;
  
      while (i >= 0) {
        if (readArray[i] < max[size - 1 - i]) {
          const number = this.makeNumber(readArray);
          if (this.isAscending(number) && i === size - 1) {
            readArray[i] = readArray[i] + 1;
            const numb = this.makeNumber(readArray);
            this.odoReading = numb;
            return(this.odoReading);
          } 
          
          else {
            let temp = readArray[i] + 1;
            for (let varIndex = i; varIndex <= size - 1; varIndex++) {
              readArray[varIndex] = temp++;
            }
            const result = this.makeNumber(readArray);
           this.odoReading = result;
           return(this.odoReading);
          }
        } else if (readArray[i] === max[size - 1 - i]) {
          i--;
        }
      }
    }
  
    return 1;
  }
  
  

  decrement(): number {
    if (this.isAscending(this.odoReading)) {
      const readArray = this.makeArray(this.odoReading);
      const max = [9, 8, 7, 6, 5, 4, 3, 2, 1];
      const size = readArray.length;
      let i = size - 1;
  
      while (i >= 0) {
        if (i !== 0 && readArray[i] !== readArray[i - 1] + 1) {
          readArray[i] = readArray[i] - 1;
          const numb = this.makeNumber(readArray);
          this.odoReading = numb;
          return(this.odoReading);
        } else {
          if (i !== 0) {
            readArray[i] = max[size - 1 - i];
            i--;
          } else {
            readArray[i] = readArray[i] - 1;
            const numb = this.makeNumber(readArray);
           this.odoReading = numb;
           return(this.odoReading);
          }
        }
      }
    }
  
    return 0;
  }
  
  isAscending(num: number): boolean {
    const numStr = num.toString();
    for (let i = 0; i < numStr.length - 1; i++) {
      if (numStr[i] > numStr[i + 1]) {
        return false;
      }
    }
    return true;
  }
  
  makeArray(num: number): number[] {
    const numStr = num.toString();
    return numStr.split('').map((char) => parseInt(char, 10));
  }
  
  makeNumber(array: number[]): number {
    return parseInt(array.join(''), 10);
  }

}


