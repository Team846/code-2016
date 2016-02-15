# Robot Tasks
## Combined
- [ ] Cross each type of defense
- [ ] Move collector and shooter into slightly upright position for moving around
  - [ ] Optional: run after collect
- [ ] Shoot ball (low)
  - [ ] Move collector to collecting setpoint and set rollers to purge direction
  - [ ] Transfer ball from shooter to collector

## Collector
- [ ] Override collector spinners
- [ ] Override collector movement
- [ ] Move collector to given position
- [ ] Move collector to given setpoint
  - [ ] Collecting
  - [ ] Midway (for portcullis)
  - [ ] Stowed (facing up, safety should stop motion if shooter is also stowed)
- [ ] Collect ball
  - [ ] Roll spinners to suck in ball
  - [ ] Simultaneously move to collecting setpoint
  - [ ] Have shooter ready and spinning to receive the ball
  - [ ] When IR sensor detects ball, lift collector slightly to allow for easy passage into shooter

## Shooter
- [ ] Override shooter spinners
- [ ] Override shooter movement
- [ ] Move shooter to given position
- [ ] Move shooter to given setpoint
  - [ ] Collecting (transferring ball from collector)
  - [ ] Stowed (facing down, safety should stop motion if collector is also stowed)
- [ ] Shoot ball (high)
  - [ ] Roll flywheel to set RPM
  - [ ] Simultaneously move to shooting setpoint
  - [ ] Once at target, push ball through flywheel
