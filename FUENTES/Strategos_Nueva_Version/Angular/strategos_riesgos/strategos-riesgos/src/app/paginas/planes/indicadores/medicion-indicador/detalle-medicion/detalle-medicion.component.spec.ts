import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DetalleMedicionComponent } from './detalle-medicion.component';

describe('DetalleMedicionComponent', () => {
  let component: DetalleMedicionComponent;
  let fixture: ComponentFixture<DetalleMedicionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DetalleMedicionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DetalleMedicionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
