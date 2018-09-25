package hr.fer.zemris.ppj.lab03;

import hr.fer.zemris.ppj.lab01.Symbol;
import hr.fer.zemris.ppj.lab01.Token;
import hr.fer.zemris.ppj.lab02.Node;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class VariableScopeChecker {

    private Node root;
    private List<String> output;

    public VariableScopeChecker(Node root) {
        this.root = root;
    }

    public List<String> checkScopes(){

        output = new LinkedList<>();
        checkGroupScope(root, new HashMap<>(), new HashMap<>());
        return output;

    }

    private void checkGroupScope(Node root, Map<String, Integer> parentVariables, Map<String, Integer> currentVariables){

        Token token = root.token();

        if ( token.symbol().equals(Symbol.IDN)){
            if ( token.next().symbol().equals(Symbol.OP_ASSIGN) || token.next().symbol().equals(Symbol.FROM)){
                currentVariables.put(token.value(), token.lineIndex());
            } else {
                Integer declarationIndex = currentVariables.containsKey(token.value()) ? currentVariables.get(token.value()) : parentVariables.containsKey(token.value()) ? parentVariables.get(token.value()) : -1;
                output.add(String.format("%d %d %s", token.lineIndex(), declarationIndex, token.value()));
            }
        }

        for ( Node child : root.children() ){
            if ( child.token().symbol().isCreatesScope() ){
                checkGroupScope(child, new HashMap<>(parentVariables){{putAll(currentVariables);}}, new HashMap<>());
            } else {
                checkGroupScope(child, parentVariables, currentVariables);
            }
        }
    }

}
