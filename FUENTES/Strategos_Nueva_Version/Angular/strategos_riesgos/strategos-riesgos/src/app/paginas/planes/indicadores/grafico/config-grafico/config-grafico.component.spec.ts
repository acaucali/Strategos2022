import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfigGraficoComponent } from './config-grafico.component';

describe('ConfigGraficoComponent', () => {
  let component: ConfigGraficoComponent;
  let fixture: ComponentFixture<ConfigGraficoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ConfigGraficoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ConfigGraficoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
