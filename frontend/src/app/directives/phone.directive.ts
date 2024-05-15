import { Directive, HostListener } from '@angular/core';
import { NgControl } from '@angular/forms';

@Directive({
  selector: '[appPhone]',
  standalone: true
})
export class PhoneDirective {

  constructor(public ngControl: NgControl) { }

@HostListener('input', ['$event']) onInput(event: Event) {
    const input = event.target as HTMLInputElement;
    let value = input.value.replace(/\D/g, ''); 
    if (value.length > 0) {
      const matches = value.match(new RegExp('.{1,3}', 'g'));
      if (matches) {
        value = matches.join('-'); // Add '-' after every 3 characters
      }
    }
    input.value = value;
  }
}
