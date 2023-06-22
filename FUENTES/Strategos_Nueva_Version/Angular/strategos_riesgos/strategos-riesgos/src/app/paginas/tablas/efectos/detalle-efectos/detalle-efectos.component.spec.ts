import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DetalleEfectosComponent } from './detalle-efectos.component';

describe('DetalleEfectosComponent', () => {
  let component: DetalleEfectosComponent;
  let fixture: ComponentFixture<DetalleEfectosComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DetalleEfectosComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DetalleEfectosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
