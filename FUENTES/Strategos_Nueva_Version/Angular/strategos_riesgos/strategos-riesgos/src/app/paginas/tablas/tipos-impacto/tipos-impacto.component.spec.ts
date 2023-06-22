import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TiposImpactoComponent } from './tipos-impacto.component';

describe('TiposImpactoComponent', () => {
  let component: TiposImpactoComponent;
  let fixture: ComponentFixture<TiposImpactoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TiposImpactoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TiposImpactoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
