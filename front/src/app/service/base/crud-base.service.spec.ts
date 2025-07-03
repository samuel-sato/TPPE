import { TestBed } from '@angular/core/testing';

import { CrudBaseService } from './crud-base.service';

describe('CrudBaseService', () => {
  let service: CrudBaseService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CrudBaseService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
