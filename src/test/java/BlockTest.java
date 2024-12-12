
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class BlockTest {

    private boolean initialized = false;
    private List<Block> blockchain;
    private Block genesisBlock;
    private int prefix;
    private String prefixString;

    @Before
    public void initialize() {
        if (!this.initialized) {
            this.blockchain = new ArrayList<>();
            this.prefix = 4;
            this.prefixString = new String(new char[prefix]).replace('\0', '0');

            this.initialized = true;
        }
    }

    @Test
    public void givenBlockchain_whenNewBlockAdded_thenSucces() {
        this.genesisBlock = new Block("Genesis Block", "0", new Date().getTime());
        blockchain.add(genesisBlock);
        Block newBlock = new Block(
                "The is a New Block.",
                blockchain.get(blockchain.size() - 1).getHash(),
                new Date().getTime());
        newBlock.mineBlock(prefix);
        assertTrue(newBlock.getHash().substring(0, prefix).equals(prefixString));
        blockchain.add(newBlock);
        System.out.println(blockchain.get(0).getHash());
        System.out.println(blockchain.get(1).getHash() + ":" + blockchain.get(1).getPreviousHash());
    }

    @Test
    public void givenBlockchain_whenValidated_thenSuccess() {
        this.genesisBlock = new Block("Genesis Block", "0", new Date().getTime());
        blockchain.add(genesisBlock);
        Block newBlock = new Block(
                "The is a New Block.",
                blockchain.get(blockchain.size() - 1).getHash(),
                new Date().getTime());
        newBlock.mineBlock(prefix);
        blockchain.add(newBlock);
        
        boolean flag = true;     
        for (int i = 1; i < blockchain.size(); i++) {
            System.out.println(blockchain.get(i).getHash());
            String previousHash = i == 0 ? "0" : blockchain.get(i - 1).getHash();
            flag = blockchain.get(i).getHash().equals(blockchain.get(i).calculateBlockHash())
                    && previousHash.equals(blockchain.get(i).getPreviousHash())
                    && blockchain.get(i).getHash().substring(0, prefix).equals(prefixString);
            if (!flag) {
                break;
            }
        }
        assertTrue(flag);
    }
}
