import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DetalleIniciativaComponent } from './detalle-iniciativa.component';

describe('DetalleIniciativaComponent', () => {
  let component: DetalleIniciativaComponent;
  let fixture: ComponentFixture<DetalleIniciativaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DetalleIniciativaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DetalleIniciativaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
