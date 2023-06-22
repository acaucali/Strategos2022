import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DetalleControlesComponent } from './detalle-controles.component';

describe('DetalleControlesComponent', () => {
  let component: DetalleControlesComponent;
  let fixture: ComponentFixture<DetalleControlesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DetalleControlesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DetalleControlesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
