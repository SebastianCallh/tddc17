(define (problem navigate)
  (:domain shakey)
  (:objects 
    left_hand  - hand 
    right_hand - hand 
    green_ball - ball
    red_ball   - ball
    room11      - room
    room12      - room
    room13      - room
    room21      - room
    room22      - room
    room23      - room
    room31      - room
    room32      - room
    room33      - room)
  (:init
    (box room11)

    (ball_pos green_ball room13)
    (ball_pos red_ball room22)

    (wide_door room11 room12)
    (wide_door room12 room22)
    (wide_door room13 room23)
    (wide_door room21 room22)
    (wide_door room22 room23)

    (small_door room23 room33)
    (small_door room33 room32)
    (small_door room32 room31)

    (lit room31)
    (lit room32)
    (lit room33)

    (pos room31)

    (empty_hand right_hand)
    (empty_hand left_hand))

  (:goal (and (ball_pos green_ball room31) (ball_pos red_ball room11))))