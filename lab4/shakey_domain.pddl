(define (domain shakey)
  (:requirements :strips)
  (:types hand room ball)
  (:predicates 
    (pos  ?r - room)
    (lit  ?r - room)
    (box  ?r - room)
    (ball_in_hand ?b - ball ?h - hand )
    (ball_pos     ?b - ball ?r - room)
    (empty_hand   ?h - hand)
    (small_door   ?r1 - room ?r2 - room)
    (wide_door    ?r1 - room ?r2 - room))

  (:action move
    :parameters   (?from - room ?to - room)
    :precondition (and (pos ?from) (or 
    		  (wide_door ?from ?to) 
		  (small_door ?from ?to) 
		  (wide_door ?to ?from) 
		  (small_door ?to ?from)))
    :effect       (and (not (pos ?from)) (pos ?to)))

  (:action pick_up
    :parameters   (?h - hand ?b - ball ?r - room)
    :precondition (and (ball_pos ?b ?r) (pos ?r) (lit ?r) (empty_hand ?h))
    :effect       (and (not (empty_hand ?h)) (ball_in_hand ?b ?h) (not (ball_pos ?b ?r))))

  (:action drop
    :parameters   (?h - hand ?b - ball ?r - room)
    :precondition (and (ball_in_hand ?b ?h) (pos ?r))
    :effect       (and (empty_hand ?h) (not (ball_in_hand ?b ?h)) (ball_pos ?b ?r)))

  (:action light_room
    :parameters   (?r - room)
    :precondition (and (not (lit ?r)) (box ?r) (pos ?r))
    :effect       (lit ?r))

  (:action push_box
    :parameters   (?r1 - room ?r2 - room)
    :precondition (and (box ?r1) (pos ?r1) (or (wide_door ?r1 ?r2) (wide_door ?r2 ?r1)))
    :effect       (and (not (box ?r1)) (not (pos ?r1)) (box ?r2) (pos ?r2))))
