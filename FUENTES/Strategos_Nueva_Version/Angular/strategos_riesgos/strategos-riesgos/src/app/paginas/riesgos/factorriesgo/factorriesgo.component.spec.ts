import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FactorriesgoComponent } from './factorriesgo.component';

describe('FactorriesgoComponent', () => {
  let component: FactorriesgoComponent;
  let fixture: ComponentFixture<FactorriesgoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FactorriesgoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FactorriesgoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
