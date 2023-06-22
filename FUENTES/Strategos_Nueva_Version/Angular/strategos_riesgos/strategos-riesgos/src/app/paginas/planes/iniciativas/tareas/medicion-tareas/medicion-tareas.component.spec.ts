import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MedicionTareasComponent } from './medicion-tareas.component';

describe('MedicionTareasComponent', () => {
  let component: MedicionTareasComponent;
  let fixture: ComponentFixture<MedicionTareasComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MedicionTareasComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MedicionTareasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
