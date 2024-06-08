# runtime

The runtime combines the structural and behavioral aspects of a story to make the story progress.

## Structural

- Element
    - Node
    - Linkage

## Behavioral

- Context
    - Action
    - Message

```mermaid
stateDiagram-v2
    state "RuntimeReady" as ready
    state "RuntimeActing" as acting
    state "RuntimePaused" as paused
    state "RuntimeBlocked" as blocked
    state "RuntimeBranching" as branching
    state "RuntimeFinished" as finished
    [*] --> ready
    state has_actions <<choice>>
    ready --> has_actions
    has_actions --> acting: node has actions
    state is_blocking <<choice>>
    acting --> is_blocking
    is_blocking --> ready: node is blocking and\n has no more actions
    is_blocking --> paused: node has more actions
    paused --> ready
    state has_linkages <<choice>>
    has_actions --> has_linkages: node has no actions
    has_linkages --> branching: node has linkages
    has_linkages --> finished: node has no linkages
    state has_blocking_linkages <<choice>>
    branching --> has_blocking_linkages
    has_blocking_linkages --> blocked: linkages are blocking
    has_blocking_linkages --> ready: linkages are passthrough
    blocked --> ready
    finished --> [*]
```