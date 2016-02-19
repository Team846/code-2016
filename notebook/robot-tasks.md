# Robot Tasks
## Combined
- [ ] Cross each type of defense
- [x] Move collector and shooter into slightly upright position for moving around
  - [ ] Optional: run after collect
- [x] Shoot ball (low)
  - [x] Move collector to collecting setpoint and set rollers to purge direction
  - [x] Transfer ball from shooter to collector

## Collector
- [x] Override collector spinners
- [x] Override collector movement
- [x] Move collector to given position
- [x] Move collector to given setpoint
  - [x] Collecting
  - [x] Midway (for portcullis)
  - [x] Stowed (facing up, safety should stop motion if shooter is also stowed)
- [x] Collect ball
  - [x] Roll spinners to suck in ball
  - [x] Simultaneously move to collecting setpoint
  - [x] Have shooter ready and spinning to receive the ball
  - [x] When IR sensor detects ball, lift collector slightly to allow for easy passage into shooter

## Shooter
- [x] Override shooter spinners
- [x] Override shooter movement
- [x] Move shooter to given position
- [x] Move shooter to given setpoint
  - [x] Collecting (transferring ball from collector)
  - [x] Stowed (facing down, safety should stop motion if collector is also stowed)
- [x] Shoot ball (high)
  - [x] Roll flywheel to set RPM
  - [x] Simultaneously move to shooting setpoint
  - [x] Once at target, push ball through flywheel
