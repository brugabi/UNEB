# AGENTS.md

Behavioral guidelines to reduce common LLM coding mistakes. Merge with project-specific instructions as needed.

**Tradeoff:** These guidelines bias toward caution over speed. For trivial tasks, use judgment.

## 1. Think Before Coding

**Don't assume. Don't hide confusion. Surface tradeoffs.**

Before implementing:

- State your assumptions explicitly. If uncertain, ask.
- If multiple interpretations exist, present them - don't pick silently.
- If a simpler approach exists, say so. Push back when warranted.
- If something is unclear, stop. Name what's confusing. Ask.

## 2. Simplicity First

**Minimum code that solves the problem. Nothing speculative.**

- No features beyond what was asked.
- No abstractions for single-use code.
- No "flexibility" or "configurability" that wasn't requested.
- No error handling for impossible scenarios.
- If you write 200 lines and it could be 50, rewrite it.

Ask yourself: "Would a senior engineer say this is overcomplicated?" If yes, simplify.

## 3. Surgical Changes

**Touch only what you must. Clean up only your own mess.**

When editing existing code:

- Don't "improve" adjacent code, comments, or formatting.
- Don't refactor things that aren't broken.
- Match existing style, even if you'd do it differently.
- If you notice unrelated dead code, mention it - don't delete it.

When your changes create orphans:

- Remove imports/variables/functions that YOUR changes made unused.
- Don't remove pre-existing dead code unless asked.

The test: Every changed line should trace directly to the user's request.

## 4. Goal-Driven Execution

**Define success criteria. Loop until verified.**

Transform tasks into verifiable goals:

- "Add validation" → "Write tests for invalid inputs, then make them pass"
- "Fix the bug" → "Write a test that reproduces it, then make it pass"
- "Refactor X" → "Ensure tests pass before and after"

For multi-step tasks, state a brief plan:

```
1. [Step] → verify: [check]
2. [Step] → verify: [check]
3. [Step] → verify: [check]
```

Strong success criteria let you loop independently. Weak criteria ("make it work") require constant clarification.

---

**These guidelines are working if:** fewer unnecessary changes in diffs, fewer rewrites due to overcomplication, and clarifying questions come before implementation rather than after mistakes.

# Repository Guidelines

- This project is used for homework and tasks from the Graph Theory discipline. Work as a programmer with good graph theory knowledge, keeping the code simple enough for an academic assignment.

## Authoritative specification — read first

- `codigobase.py` is the main module. Add new graph algorithms there while keeping the existing structure cohesive.
- `instrucoes_tarefas_teams.md` stores the current and future Teams homework statements. Read it before implementing a new task, and update it when the user provides a new Teams instruction.
- Always wait for the current task or homework statement before changing behavior, unless the user specifically asks otherwise.
- `relatorio.md` should describe the current task, the implemented idea, and the expected results.
- `grafo.txt` is the default input file for interactive runs. If the current task needs a different graph format, update it with a valid small example.
- Interactive prompts that accept an input file must clearly state that pressing Enter uses `grafo.txt`.
- Vertex names typed by the user should be handled case-insensitively when a task asks for vertices, but outputs and visualizations should preserve the original labels from the graph file.

## Project Structure & Module Organization

This repository contains a small Python graph analysis script:

- `codigobase.py`: main module and executable script. It reads graph files, builds NetworkX graphs, runs graph algorithms, and displays graph visualizations.
- `grafo.txt`: default graph input used when the interactive menu receives Enter without another file name.
- `grafo01.txt`: sample undirected, unweighted graph input.
- `digrafo01.txt`: sample directed, weighted graph input.
- `relatorio.md`: report describing the implemented algorithms and expected outputs.
- `requirements.txt`: pinned Python dependencies.
- `venv/`: local virtual environment; do not edit or commit generated environment files.

Graph input files use the first line as graph metadata: `G N`, `G W`, `D N`, or `D W`. Remaining lines are edges (`u v`) or weighted edges (`u v w`).

## Build, Test, and Development Commands

Create or refresh a local environment:

```bash
python3 -m venv venv
source venv/bin/activate
pip install -r requirements.txt
```

Run the script:

```bash
python codigobase.py
```

Depending on the current homework, the script prints algorithm results and may open or save Matplotlib graph visualizations.

## Coding Style & Naming Conventions

Use Python 3 with 4-space indentation. Keep function and variable names in `snake_case`, matching the existing Portuguese naming style such as `ler_grafo_arquivo`, `adicionar_aresta`, and `verificar_sequencia`. Prefer small, focused functions that accept an explicit `G` graph argument rather than relying on global state.

When adding graph algorithms, keep output messages clear and consistent with the current command-line style. Use NetworkX to store and draw graphs. Implement the requested algorithm manually when the professor asks for a specific method.

Functions should be created with clear docummentation and comments and with the writing similar of a student. DO NOT put many details and void writing like an AI agent, be similar as human as possible.

## Testing Guidelines

No automated test suite is currently present. For changes, at minimum run:

```bash
python codigobase.py
```

For new behavior, add focused tests under a future `tests/` directory using `pytest`, with names like `test_ler_grafo_arquivo.py` or `test_kruskal.py`. Match the test cases to the graph type required by the current task, including directed/undirected and weighted/unweighted examples when relevant.

## Commit & Pull Request Guidelines

This checkout has no Git history, so no project-specific commit convention is available. Use concise imperative commit messages, for example `Add DFS path counting tests` or `Fix weighted graph parsing`.

Pull requests should include a short description, the commands run for verification, and any changes to graph input formats or expected output. Include screenshots only when visualization behavior changes.

## Agent-Specific Instructions

Keep generated documentation and code changes scoped to the repository root. Do not modify `venv/` or generated local files such as `.DS_Store`.

## Criteria of acceptance

- Should not have bugs
- The code is runnable
- It attends every point asked by the professor.
- It should read like student code: clear, direct, and not over-engineered.
