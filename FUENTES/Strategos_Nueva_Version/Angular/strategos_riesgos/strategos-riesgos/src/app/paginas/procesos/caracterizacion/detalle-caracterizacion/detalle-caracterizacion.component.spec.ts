import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DetalleCaracterizacionComponent } from './detalle-caracterizacion.component';

describe('DetalleCaracterizacionComponent', () => {
  let component: DetalleCaracterizacionComponent;
  let fixture: ComponentFixture<DetalleCaracterizacionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DetalleCaracterizacionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DetalleCaracterizacionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
