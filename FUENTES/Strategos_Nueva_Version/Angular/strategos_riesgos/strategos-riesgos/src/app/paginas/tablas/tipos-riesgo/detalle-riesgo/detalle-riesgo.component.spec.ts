import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DetalleRiesgoComponent } from './detalle-riesgo.component';

describe('DetalleRiesgoComponent', () => {
  let component: DetalleRiesgoComponent;
  let fixture: ComponentFixture<DetalleRiesgoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DetalleRiesgoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DetalleRiesgoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
