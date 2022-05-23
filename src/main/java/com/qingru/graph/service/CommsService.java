package com.qingru.graph.service;

import com.qingru.graph.arangoRepository.CommsNodeRepository;
import com.qingru.graph.arangoRepository.PersonNodeRepository;
import com.qingru.graph.domain.arango.CommsNode;
import com.qingru.graph.domain.arango.PersonNode;
import com.qingru.graph.domain.neo4j.optionFive.NPersonNode5;
import com.qingru.graph.domain.neo4j.optionFive.NPersonNode5List;
import com.qingru.graph.domain.neo4j.optionFour.NCommsNode4List;
import com.qingru.graph.domain.neo4j.optionFour.NPersonNode4;
import com.qingru.graph.domain.neo4j.optionFour.NPersonNode4List;
import com.qingru.graph.domain.neo4j.optionOne.NPersonNode1;
import com.qingru.graph.domain.neo4j.optionThree.NPersonNode3;
import com.qingru.graph.domain.neo4j.optionTwo.NPersonNode2;
import com.qingru.graph.neo4jRepository.optionFive.NPersonRelationship5ListRepository;
import com.qingru.graph.neo4jRepository.optionFive.NPersonRelationship5Repository;
import com.qingru.graph.neo4jRepository.optionFour.NCommsRelationship4ListRepository;
import com.qingru.graph.neo4jRepository.optionFour.NPersonRelationship4ListRepository;
import com.qingru.graph.neo4jRepository.optionFour.NPersonRelationship4Repository;
import com.qingru.graph.neo4jRepository.optionOne.NPersonRelationship1Repository;
import com.qingru.graph.neo4jRepository.optionThree.NPersonRelationship3Repository;
import com.qingru.graph.neo4jRepository.optionTwo.NPersonRelationship2Repository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
@AllArgsConstructor
public class CommsService {

    private CommsNodeRepository commsNodeRepository;
    private NCommsRelationship4ListRepository nCommsRelationship4ListRepository;

    public CommsNode getCommsNode(String id) {
        return commsNodeRepository.findById(id).orElse(null);
    }

    public CommsNode createCommsNode(CommsNode commsNode) {
        return commsNodeRepository.save(commsNode);
    }

    //-------- OPTION 4
    public NCommsNode4List getNCommsNode4(Long id) {
        return nCommsRelationship4ListRepository.findById(id).orElse(null);
    }

    public NCommsNode4List createNCommsNode4(NCommsNode4List nCommsNode4List) {
        return nCommsRelationship4ListRepository.save(nCommsNode4List);
    }
}

