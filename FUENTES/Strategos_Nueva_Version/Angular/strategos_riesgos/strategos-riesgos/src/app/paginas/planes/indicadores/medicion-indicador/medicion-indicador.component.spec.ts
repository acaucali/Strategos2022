import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MedicionIndicadorComponent } from './medicion-indicador.component';

describe('MedicionIndicadorComponent', () => {
  let component: MedicionIndicadorComponent;
  let fixture: ComponentFixture<MedicionIndicadorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MedicionIndicadorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MedicionIndicadorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
