/*
public class AI {


	int alphabeta(node, depth, α, β, maximizingPlayer)
    if (depth = 0)
        return the heuristic value of node
    if maximizingPlayer
        for each child of node
            α := max(α, alphabeta(child, depth - 1, α, β, FALSE))
            if β ≤ α
                break (* β cut-off *)
        return α
    else
        for each child of node
            β := min(β, alphabeta(child, depth - 1, α, β, TRUE))
            if β ≤ α
                break (* α cut-off *)
        return β

출처: http://popungpopung.tistory.com/10 [포풍포풍]
}
*/